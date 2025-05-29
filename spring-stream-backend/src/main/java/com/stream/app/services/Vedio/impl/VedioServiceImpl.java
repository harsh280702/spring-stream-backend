package com.stream.app.services.Vedio.impl;


import com.stream.app.Enitiy.Vedio;
import com.stream.app.Respositry.VedioRepo;
import com.stream.app.services.Vedio.VedioService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Service
public class VedioServiceImpl implements VedioService {


    private Long nextid = 1L;


    @Value(value = "${files.video}")
    String DIR;

    @PostConstruct
    public void init(){
         File file = new File(DIR);
         if(file.exists()){
             System.out.println("folder is allready Create ");
         }
         else {
             file.mkdir();
             System.out.println("folder is create ");
         }

    }

    @Autowired
    private VedioRepo vedioRepo;



    @Override
    public Vedio save(Vedio vedio, MultipartFile file) {






        try {

            //Orginal file name
            String filename = file.getOriginalFilename();
            String contentype = file.getContentType();
            InputStream inputStream = file.getInputStream();


            //File Path

            String Cleanfilename= StringUtils.cleanPath(filename);
            String CleanContentType = StringUtils.cleanPath(contentype);
            //folder Path : Create
            String CleanfilePath= StringUtils.cleanPath(DIR);
            //Folder Path With Filename
            Path path = Paths.get(CleanfilePath,Cleanfilename);

            //Copy file to the Folder
            Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);

            //vedio meta data
            vedio.setId(String.valueOf(nextid++));
            vedio.setContentType(contentype);
            vedio.setFilepath(path.toString());
            return vedioRepo.save(vedio);

            //metadata save in the our databses
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }



    }

    @Override
    public Vedio get(String Id) {

       return  vedioRepo.findById(Id).orElse(null);
    }

    @Override
    public Vedio getVedionByTitle(String Id) {
        return null;
    }

    @Override
    public List<Vedio> allVedio() {
        return vedioRepo.findAll();
    }




}
