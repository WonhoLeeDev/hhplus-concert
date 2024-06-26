package hhplus.concert.api.balance.usecase;

import hhplus.concert.domain.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ChargeBalanceUseCaseTest {

    @Autowired
    ChargeBalanceUseCase chargeBalanceUseCase;
    @Autowired
    UserReader userReader;
    @Autowired
    BalanceHistoryWriter balanceHistoryWriter;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("포인트 충전이 동시에 발생하면, 예외를 반환하고 한 건만 충전에 성공한다.")
    @Test
    void chargeBalanceJustOneCase_Success_ifConcurrencyOccurs() {
        //given
        final User user = userReader.getUserById(1L);
        int numOfTask = 2;

        //when
        CompletableFuture<?>[] futures = IntStream.range(0, numOfTask)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    try {
                        chargeBalanceUseCase.execute(1L, 10000);
                    } catch (Exception e) {
                    }
                }))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();  // 모든 비동기 작업이 끝날 때까지 기다림

        //then
        User UpdatedUser = userReader.getUserById(1L);

        assertEquals(user.getVersion() + 1, UpdatedUser.getVersion());
        assertEquals(user.getBalance() + 10000, UpdatedUser.getBalance());
    }
}