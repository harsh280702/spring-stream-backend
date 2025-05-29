package com.stream.app.services.Vedio;

import com.stream.app.Enitiy.Vedio;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface VedioService {

    //save vedio
    Vedio save(Vedio vedio, MultipartFile file);

    // Getting Vedio  By Id
    Vedio get(String Id);


    // Getting Vedio  By Title
    Vedio getVedionByTitle(String Id);

    // Getting All Vedio
    List<Vedio> allVedio();


}

