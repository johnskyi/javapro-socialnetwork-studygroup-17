package ru.skillbox.socialnetwork.service.impl;

import com.dropbox.core.v2.files.FileMetadata;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.imgscalr.Scalr;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.config.DropBoxConfig;
import ru.skillbox.socialnetwork.data.dto.StorageResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.File;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.FileRepository;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.service.StorageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final DropBoxConfig dropBoxConfig;
    private final FileRepository postFileRepository;
    private final PersonRepo personRepository;
    //  private final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);


    @Override
    public StorageResponse upload(String type, MultipartFile multipartFile, Principal principal) {
        Person person = findPerson(principal);
        File file = saveToBD(person, multipartFile);
        return createStorageResponse(file);
    }

    @SneakyThrows
    private File saveToBD(Person person, MultipartFile multipartFile) {

        FileMetadata metadata = dropBoxConfig.getClient().files().uploadBuilder("/".concat(multipartFile.getName()))
                .uploadAndFinish(multipartFile.getInputStream());

        Set<String> mimeType = Set.of("image/gif", "image/jpeg", "image/tiff", "image/png");

        File file = new File();
        file.setOwner(person);
        file.setRelativeFilePath(metadata.getPathLower());
        if (mimeType.contains(multipartFile.getContentType())) {
            file.setRawFileURL(getCompressedImageUrl(multipartFile));
        }
        file.setFileFormat(multipartFile.getName().replaceAll(".*\\.", ""));
        file.setBytes(multipartFile.getSize());
        file.setFileType(multipartFile.getContentType());
        file.setFileName(multipartFile.getOriginalFilename());
        file.setCreatedAt(LocalDateTime.now());

        return postFileRepository.save(file);
    }

    @SneakyThrows
    private String getCompressedImageUrl(MultipartFile multipartFile) {

        BufferedImage bufferedImage = Scalr.resize(ImageIO.read(multipartFile.getInputStream()), 200);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());

        FileMetadata metadata = dropBoxConfig.getClient().files().uploadBuilder("/small-".concat(multipartFile.getName()))
                .uploadAndFinish(is);
        return metadata.getPathLower();
    }

    private StorageResponse createStorageResponse(File file) {
        return StorageResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(StorageResponse.Datum.builder()
                        .id(file.getId())
                        .ownerId(file.getOwner().getId())
                        .fileName(file.getFileName())
                        .relativeFilePath(file.getRelativeFilePath())
                        .rawFileURL(file.getRawFileURL())
                        .fileFormat(file.getFileFormat())
                        .bytes(file.getBytes())
                        .fileType(file.getFileType())
                        .createdAt(file.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                        .build())
                .build();
    }

    private Person findPerson(Principal principal) {
        if (Objects.isNull(principal)) {
            throw new PersonNotAuthorized("The Person not authorized");
        }
        return personRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
