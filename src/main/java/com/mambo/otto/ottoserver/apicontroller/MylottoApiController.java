package com.mambo.otto.ottoserver.apicontroller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.domain.MylottoRepository;
import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.SessionUser;
import com.mambo.otto.ottoserver.domain.Mylotto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MylottoApiController {
    private final MylottoRepository mylottoRepository;
    private final HttpSession session;

    @GetMapping("/lottos")
    public ResponseDto<?> home() {
        return new ResponseDto<>(1, "다 찾았습니다", mylottoRepository.findAll());
    }

    @GetMapping("/lotto/{id}")
    public ResponseDto<?> findById(@PathVariable Long id) {
        return new ResponseDto<>(1, "성공", mylottoRepository.findByUserId(id));
    }

    @PostMapping("/s/mylotto")
    public ResponseDto<?> save(@RequestBody Mylotto mylotto) {
        SessionUser sUser = (SessionUser) session.getAttribute("sessionUser");

        if (sUser == null) {
            return new ResponseDto<>(1, "로그인이 되어있지 않습니다.", null);
        }
        mylotto.setInUserId(sUser.getId());
        mylottoRepository.save(mylotto);
        return new ResponseDto<>(1, "sucess to INSERT ", mylotto);
    }

    @DeleteMapping("/d-mylotto/{id}")
    public ResponseDto<?> delete(@PathVariable Long id) {
        Mylotto myPs = mylottoRepository.findByLottoId(id);
        mylottoRepository.deleteById(id);
        return new ResponseDto<>(1, "DELETE Complited", myPs);
    }
}
