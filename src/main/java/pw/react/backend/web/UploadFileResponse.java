package pw.react.backend.web;

import lombok.*;

/** Created by Pawel Gawedzki on 06-Oct-2019. */
@Getter
@Setter
@AllArgsConstructor
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
