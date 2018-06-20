package com.gomefinance.uploadImg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomefinance.uploadImg.mapper.PictureMapper;
import com.gomefinance.uploadImg.service.PictureService;

@Service
public class PictureServiceImple implements PictureService {

	@Autowired
	private PictureMapper pictureMapper;
}
