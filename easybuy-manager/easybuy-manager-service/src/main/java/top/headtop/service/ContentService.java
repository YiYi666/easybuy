package top.headtop.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbContent;

public interface ContentService {
	public List<EUTreeNode> getContentList(@RequestParam(value="id",defaultValue="0")long parentId);

	public EasyBuyResult saveContent(long parentId, String name);

	public EasyBuyResult deleteContent(long id);

	public EasyBuyResult saveBigContent(TbContent content);
}
