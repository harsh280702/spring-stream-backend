package com.stream.app.Respositry;

import com.stream.app.Enitiy.Vedio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VedioRepo extends JpaRepository<Vedio,String> {



}
