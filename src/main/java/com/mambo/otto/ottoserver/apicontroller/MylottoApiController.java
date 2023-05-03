package com.mambo.otto.ottoserver.apicontroller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.MylottoReqDto.MylottoDeleteReqDto;
import com.mambo.otto.ottoserver.dto.MylottoReqDto.MylottoSaveReqDto;
import com.mambo.otto.ottoserver.service.MylottoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MylottoApiController {
    private final MylottoService mylottoService;

    @GetMapping("/lottos")
    public ResponseDto<?> findAll() {
        return new ResponseDto<>(1, "다 찾았습니다", mylottoService.findAll());
    }

    @GetMapping("/lotto")
    public ResponseDto<?> findByUserId() {
        // find by Session data
        return new ResponseDto<>(1, "성공", mylottoService.findByUserId());
    }

    @PostMapping("/s/mylotto")
    public ResponseDto<?> save(@RequestBody MylottoSaveReqDto mylotto) {
        mylottoService.save(mylotto);
        return new ResponseDto<>(1, "sucess to INSERT ", mylotto);
    }

    @DeleteMapping("/s/d-mylotto")
    public ResponseDto<?> delete(@RequestBody MylottoDeleteReqDto inMylottoId) {
        String[] msg = { "Denied DELETE Access", "DELETE Complited", "NO Selected DELETE ITEM" };
        int result = mylottoService.deleteBylottoId(inMylottoId);
        return new ResponseDto<>(result, msg[result], null);

    }
}
