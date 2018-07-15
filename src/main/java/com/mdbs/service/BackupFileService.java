package com.mdbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdbs.mapper.BackupFileMapper;
import com.mdbs.pojo.BackupFile;

@Service
public class BackupFileService {

	@Autowired
	BackupFileMapper backupFileMapper;
	public List<BackupFile> getFileListByTime(int bid) {
		return backupFileMapper.getFileListByTime(bid);
	}
}
