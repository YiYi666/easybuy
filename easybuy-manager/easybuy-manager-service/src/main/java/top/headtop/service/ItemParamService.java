package top.headtop.service;

import top.headtop.pojo.EasyBuyResult;

public interface ItemParamService {

	EasyBuyResult queryParamByCatId(Long catId);

	EasyBuyResult saveParamGroupKey(Long catId, String paramData);

}
