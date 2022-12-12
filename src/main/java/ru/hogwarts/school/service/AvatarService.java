package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepositories;
import ru.hogwarts.school.repositories.StudentRepositories;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    private final AvatarRepositories avatarRepositories;
    private final StudentRepositories studentRepositories;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(AvatarRepositories avatarRepositories, StudentRepositories studentRepositories) {
        this.avatarRepositories = avatarRepositories;
        this.studentRepositories = studentRepositories;
    }

    public void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException {
        Student student = studentRepositories.getById(id);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepositories.save(avatar);
    }

    private byte[] generateDateForDB(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getHeight() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();
            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long id) {
        return avatarRepositories.findByStudentId(id).orElse(new Avatar());
    }
}
