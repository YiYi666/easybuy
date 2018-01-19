package top.headtop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import top.headtop.pojo.PicResult;
import top.headtop.utils.FastDFSClient;

@Service
public class PicServiceImpl implements PicService{
	
	@Value("${IMG_PREFIX_URL}")
	private String IMG_PREFIX_URL;
	
	@Override
	public PicResult uploadFile(MultipartFile uploadFile) {
		PicResult result = new PicResult();
		if(uploadFile.isEmpty()){
			result.setMessage("图片为空，不能上传！");
			result.setError(1);
		}else{
			try {
				FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
				String filename = uploadFile.getOriginalFilename();
				String extName = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
				result.setError(0);
				result.setUrl(IMG_PREFIX_URL+url);
				result.setMessage("图片上传成功！");
				
			} catch (Exception e) {
				result.setError(1);
				result.setMessage("图片上传失败！");
				e.printStackTrace();
			}
		}
		
		return result;
	}



}
