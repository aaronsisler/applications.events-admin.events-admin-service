package com.ebsolutions.eventsadminservice.filegeneration;

import com.ebsolutions.eventsadminservice.shared.util.FileLocationUtil;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {
  private final FileDao fileDao;

  public URL getFileUrl(String establishmentId, String filename) {
    String fileLocation = FileLocationUtil.build(establishmentId, filename);

    return this.fileDao.createPresignedUrl(fileLocation);
  }
}
