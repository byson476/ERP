package com.erp.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.user.UserService;
import com.erp.user.dto.UserDto;
import com.erp.user.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {
  private final UserService userService;
  
  @GetMapping("/api/member/kakao")
  public ResponseEntity<Response> getMemberFromKakao(@RequestParam("accessToken") String accessToken) {
    log.info("access Token ");
    log.info(accessToken);
    UserDto userDto = userService.getKakaoMember(accessToken);
    
    Map<String, Object> claims = userDto.getClaims();
    String jwtAccessToken = JWTUtil.generateToken(claims, 10);
    String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24);
    claims.put("accessToken", jwtAccessToken);
    claims.put("refreshToken", jwtRefreshToken);

    Response response = new Response();
    response.setStatus(ResponseStatusCode.LOGIN_SUCCESS);
    response.setMessage(ResponseMessage.LOGIN_SUCCESS);
    response.setData(claims);
    /*
     * HttpHeaders httpHeaders = new HttpHeaders();
     * httpHeaders.setContentType(new MediaType(MediaType.APPLICATION_JSON,
     * Charset.forName("UTF-8")));
     * ResponseEntity<Response> responseEntity = new
     * ResponseEntity<Response>(response, httpHeaders, HttpStatus.OK);
     */
    return ResponseEntity.ok(response);

  }

  /*
   * @PutMapping("/api/member/modify/{email}")
   * public Map<String, String> modify(@RequestBody UserDto userDto) throws
   * Exception {
   * log.info("user modify: " + userDto);
   * userService.update(userDto);
   * return Map.of("result", "modified");
   * 
   * }
   */

}
