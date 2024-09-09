package com.devkduck.duckshop.service;

import com.devkduck.duckshop.dto.ItemFormDto;
import com.devkduck.duckshop.entity.Item;
import com.devkduck.duckshop.entity.ItemImg;
import com.devkduck.duckshop.repository.ItemImgRepository;
import com.devkduck.duckshop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        for(int i=0; i<itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");
            itemImgService.saveItemImg(itemImg,itemImgFileList.get(i)); //상품 이미지 정보 저장
        }
        return item.getId();
    }
}
