package ru.skillbox.socialnetwork.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.utils.YamlPropertySourceFactory;

import java.io.IOException;
import java.util.Map;

@Component
@PropertySource(value = "classpath:cloudinary.yaml", factory = YamlPropertySourceFactory.class)
public class CloudinaryConfig {

    @Value("${cloud_name}")
    private String cloudName;

    @Value("${api_key}")
    private String apiKey;

    @Value("${api_secret}")
    private String apiSecret;

    public Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public Map<String, Object> uploadFileWithParam(MultipartFile file, Map<String, String> options) throws IOException {
       return getCloudinary()
               .uploader()
               .upload(file.getBytes(), options);
    }

    public String scaleUploadedImage(String publicId, int scaleWidth){
       return getCloudinary()
               .url()
               .transformation(new Transformation().crop("scale").width(scaleWidth))
               .generate(publicId);
    }
}
