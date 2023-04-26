package com.mambo.otto.ottoserver.apicontroller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.domain.Mylotto;
import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.MylottoReqDto.MylottoDeleteReqDto;
import com.mambo.otto.ottoserver.dto.MylottoReqDto.MylottoSaveReqDto;
import com.mambo.otto.ottoserver.service.MylottoService;

import lombok.RequiredArgsConstructor;

/**
 * AUTH : SW
 * FUNCTION : Recieve Clients Request with JSON
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : 2023.05.09( SW )
 * 
 * <pre>
 * Mylotto 객체 관련 요청 관리 컨트롤러
 * </pre>
 * 
 * @JwtAuthorizationFilter : "/s/mylotto" Request Handling to Chencking Auth
 * @findByUserId : Edit Response;
 * 
 */

@RestController
@RequiredArgsConstructor
public class MylottoApiController {
    private final MylottoService mylottoService;

    @GetMapping("/lottos")
    public ResponseDto<?> findAll() {
        return new ResponseDto<>(200, "모든 번호 정보를 찾았습니다", mylottoService.findAll());
    }

    @GetMapping("/lotto/{id}")
    public ResponseDto<?> findByUserId(@PathVariable(required = false) Long id) {
        // find by Session data
        List<Mylotto> mylottoList = mylottoService.findByUserId(id);
        if (mylottoList == null) {
            return new ResponseDto<>(-1, "로그인 정보가 정확하지 않습니다 다시 로그인 해주십시오", null);
        }

        return new ResponseDto<>(200, "번호 찾음", mylottoList);
    }

    @PostMapping("/s/mylotto")
    public ResponseDto<?> save(@RequestBody MylottoSaveReqDto mylotto) {
        mylottoService.save(mylotto);
        return new ResponseDto<>(200, "정상적으로 저장이 되었습니다", mylotto);
    }

    @DeleteMapping("/s/d-mylotto")
    public ResponseDto<?> delete(@RequestBody MylottoDeleteReqDto inMylottoId) {
        String[] msg = { "Denied DELETE Access", "DELETE Complited", "NO Selected DELETE ITEM" };
        int result = mylottoService.deleteBylottoId(inMylottoId);
        return new ResponseDto<>(result, msg[result], null);

    }
}
