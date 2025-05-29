package com.stream.app.Controller;


import com.stream.app.AppConstrainer;
import com.stream.app.Enitiy.Vedio;
import com.stream.app.services.Vedio.VedioService;
import org.hibernate.boot.model.source.spi.SingularAttributeSourceToOne;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class VedioController {


    VedioService vedioService;

    public VedioController(VedioService vedioService) {
        this.vedioService = vedioService;
    }




    @PostMapping
    public ResponseEntity<Vedio>create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title
            ){

        Vedio vedio = new Vedio();
        vedio.setTitle(title);



      Vedio vedio1  = vedioService.save(vedio,file);
      if( vedio1 !=null){
          return new ResponseEntity<>(vedio, HttpStatus.OK);
      }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @GetMapping("/stream/{Id}")
    public ResponseEntity<Resource> stream( @PathVariable String Id ){

         Vedio  vedio= vedioService.get(Id);
          String contentType = vedio.getContentType();
          String FilePath = vedio.getFilepath();


          Resource resource= new FileSystemResource(FilePath);


        if(contentType==null){
            contentType  = "application/octet-stream";
        }

          return  ResponseEntity.ok()
                  .contentType(MediaType.parseMediaType(contentType))
                  .body(resource);




    }

    @GetMapping
    public  ResponseEntity<List<Vedio>> getAllVideo(){
        return new ResponseEntity<>(vedioService.allVedio(),HttpStatus.OK);
    }



    //sned the video cunks in the client server

    @GetMapping("/stream/vedio/{id}")
    public ResponseEntity<Resource> streamVideo(
            @PathVariable String Id,
            @RequestHeader (value ="Range", required = false) String range
    ) {


        System.out.println(range);

        Vedio vedio = vedioService.get(Id);
        Path path = Paths.get(vedio.getFilepath());
        Resource resource = new FileSystemResource(path);
        String contentType = vedio.getContentType();


        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        Long fileLength = path.toFile().length();

        if (range == null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }

        Long rangeStart;
        Long rangeEnd;

        String[] ranges = range.replace("bytes", "").split("-");
        rangeStart = Long.parseLong(ranges[0]);

        rangeEnd = rangeStart+ AppConstrainer.CHUNK_SIZE-1;
        if( rangeEnd>= fileLength)
        {
             rangeEnd = fileLength-1;

        }
//        if (range.length() > 1) {
//            rangeEnd = Long.parseLong(ranges[1]);
//
//        } else {
//
//            rangeEnd = fileLength - 1;
//        }
//
//        if (rangeEnd > fileLength - 1) {
//            rangeEnd = fileLength - 1;
//
//        }


        InputStream inputStream;
        try {
            inputStream = Files.newInputStream(path);
            inputStream.skip(rangeStart);
            Long contentLenght = rangeEnd + rangeStart + 1;


            byte[] data = new byte[Math.toIntExact(contentLenght)];
          int read = inputStream.read(data, 0, data.length);



            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
            headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
            headers.add("Pragram", "no-cache");
            headers.add("Expires", "0");
            headers.add("X-Content-Type-Options", "nosniff");

            headers.setContentLength(contentLenght);
            return ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new ByteArrayResource(data));

        } catch (IOException ex) {

            return ResponseEntity.internalServerError().build();
        }



    }

}