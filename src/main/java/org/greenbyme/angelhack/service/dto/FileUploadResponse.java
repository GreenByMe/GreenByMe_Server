package org.greenbyme.angelhack.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FileUploadResponse {

    private final String fileName;
    private final String filedUrl;
    private final String fileType;
    private final long size;

}
