# ModelMapper ApplicationConfig 설정 설명

## 개요

`ApplicationConfig` 클래스는 ModelMapper를 스프링 빈으로 등록하고 DTO ↔
Entity 매핑을 더 편리하게 수행하기 위한 설정을 담고 있습니다.

------------------------------------------------------------------------

## 코드

``` java
@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper;
    }
}
```

------------------------------------------------------------------------

## @Configuration

스프링에게 이 클래스가 설정 파일임을 알려주는 애노테이션입니다.

------------------------------------------------------------------------

## @Bean

`ModelMapper` 객체를 스프링 컨테이너에 싱글톤으로 등록합니다.

------------------------------------------------------------------------

## ModelMapper 설정 상세 설명

### 1. setFieldMatchingEnabled(true)

필드 기반 매핑을 활성화하여 private 필드도 매핑할 수 있습니다.

### 2. setFieldAccessLevel(PRIVATE)

getter/setter가 없어도 private 필드에 접근하여 매핑할 수 있게 해줍니다.

### 3. setMatchingStrategy(MatchingStrategies.LOOSE)

유연한 매칭 전략으로 필드명이 정확히 일치하지 않아도 자동 매핑됩니다.

------------------------------------------------------------------------

## 요약

  설정                            역할
  ------------------------------- -----------------------
  setFieldMatchingEnabled(true)   필드 기반 매핑 활성화
  setFieldAccessLevel(PRIVATE)    private 필드도 매핑
  setMatchingStrategy(LOOSE)      유연한 매칭 전략

------------------------------------------------------------------------

## 목적

DTO ↔ Entity 변환 로직을 자동화하여 개발 생산성을 크게 높이기 위한
설정입니다.
