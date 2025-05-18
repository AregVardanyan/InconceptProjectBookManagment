package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FilenameUtils;
import org.example.inconceptproject.enums.FileDownloadStatus;
import org.example.inconceptproject.model.Edition;
import org.example.inconceptproject.model.FileInfo;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CoverImageDeserializer extends JsonDeserializer<FileInfo> {

    @Override
    public FileInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if( p.getText() == null || p.getText().isBlank() || p.getText().isEmpty()){
            return null;
        }
        return FileInfo.builder().fileUrl(p.getText()).status(FileDownloadStatus.PENDING).build();
    }
}
