package com.mambo.otto.ottoserver.apicontroller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.domain.MylottoRepository;
import com.mambo.otto.ottoserver.domain.Mylotto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MylottoApiController {
    private final MylottoRepository mylottoRepository;

    @GetMapping("/lottos")
    public Object home() {
        return mylottoRepository.findAll();
    }

    @GetMapping("/lotto/{id}")
    public List<Mylotto> findById(@PathVariable Long id) {
        return mylottoRepository.findByUserId(id);
    }

    @PostMapping("/mylotto")
    public String inesrt(@RequestBody Mylotto mylotto) {
        mylottoRepository.save(mylotto);
        return "lotto insert OK";
    }

    @DeleteMapping("/d-mylotto/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("나실행됨?" + id);
        mylottoRepository.deleteById(id);
        return "lotto insert OK";
    }

}
