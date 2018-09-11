package br.com.intelliapps.jointedtrust.main.components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaverComponent {

	private final Path rootLocation = Paths.get("filestorage").toAbsolutePath().normalize();

	@PostConstruct
	public void createRootDirectory() {
		try {
			if (Files.notExists(this.rootLocation))
				Files.createDirectories(this.rootLocation);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Map<String, String> write(String baseFolder, MultipartFile[] files, String objTitle) {

		Map<String, String> returnMap = new HashMap<>();

		try {

			Arrays.asList(files).stream().forEach(file -> {
				try {
					String fileName = StringUtils.cleanPath(baseFolder + "/" + objTitle + "/" + file.getOriginalFilename());
					Path targetLoc = this.rootLocation.resolve(fileName);
					if (Files.notExists(targetLoc)) {
						Files.createDirectories(targetLoc);
					}
					Files.copy(file.getInputStream(), targetLoc, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				returnMap.put(file.getOriginalFilename(), rootLocation + "/" + baseFolder + "/" + objTitle + "/" + file.getOriginalFilename());
			});
			return returnMap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public String write(String baseFolder, MultipartFile file, String objTitle) {

		String returnFileAddr = "";

		try {

			String fileName = StringUtils.cleanPath(objTitle + "/" + file.getOriginalFilename());
			Path targetLoc = this.rootLocation.resolve(fileName);
			if (Files.notExists(targetLoc)) {
				Files.createDirectories(targetLoc);
			}
			Files.copy(file.getInputStream(), targetLoc, StandardCopyOption.REPLACE_EXISTING);
			returnFileAddr = rootLocation + "/" + objTitle + "/" + file.getOriginalFilename();
			return returnFileAddr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
