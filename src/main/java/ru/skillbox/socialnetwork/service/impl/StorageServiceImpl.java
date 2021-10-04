package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.config.CloudinaryConfig;
import ru.skillbox.socialnetwork.data.dto.StorageResponse;
import ru.skillbox.socialnetwork.data.entity.File;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.FileRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.service.StorageService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final CloudinaryConfig cloudinaryConfig;
    private final FileRepository postFileRepository;
    private final PersonRepo personRepository;
    private final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);


    @Override
    public StorageResponse upload(String type, MultipartFile multipartFile, Principal principal) {
        Person person = findPerson(principal);
        File file = saveToBD(person, multipartFile);
        return createStorageResponse(file);
    }

    @SneakyThrows
    private File saveToBD(Person person, MultipartFile multipartFile) {

        Map<String, Object> uploadFileMetaData = cloudinaryConfig.uploadFileWithParam(multipartFile, Map.of());
        logger.info("upload file url:\n {}", uploadFileMetaData.get("url"));

        String smallImageUrl = "";
        if (uploadFileMetaData.get("resource_type").equals("image")) {
            smallImageUrl = cloudinaryConfig.scaleUploadedImage((String) uploadFileMetaData.get("public_id"), 200);
            logger.info("scaled image url:\n {}", smallImageUrl);
        }

        File file = new File();
        file.setOwner(person);
        file.setRelativeFilePath((String) uploadFileMetaData.get("url"));
        file.setRawFileURL(smallImageUrl);
        file.setFileFormat((String) uploadFileMetaData.get("format"));
        file.setBytes(multipartFile.getSize());
        file.setFileType((String) uploadFileMetaData.get("resource_type"));
        file.setFileName(multipartFile.getOriginalFilename());
        file.setCreatedAt(LocalDateTime.now());

        return postFileRepository.save(file);
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
