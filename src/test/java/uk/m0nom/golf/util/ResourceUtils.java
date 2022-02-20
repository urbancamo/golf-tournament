package uk.m0nom.golf.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {
    public static String readResource(ResourceLoader resourceLoader, String path) throws IOException {
        Resource resource = resourceLoader.getResource(String.format("classpath:%s", path));
        InputStream inputStream = resource.getInputStream();
        byte[] fileData = FileCopyUtils.copyToByteArray(inputStream);
        return new String(fileData);
    }
}
