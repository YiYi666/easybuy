package top.headtop.service;

import org.springframework.web.multipart.MultipartFile;

import top.headtop.pojo.PicResult;

public interface PicService {

	PicResult uploadFile(MultipartFile uploadFile);

}
