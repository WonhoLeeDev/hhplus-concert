package hhplus.concert.api.queue.controller;

import hhplus.concert.api.mock.FakeStore;
import hhplus.concert.api.mock.dto.response.user.QueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class MockQueueController {

    private final FakeStore fakeStore;

    @GetMapping("{id}")
    public QueueResponse queue(@PathVariable long id) {
        return fakeStore.createFakeQueue();
    }

}
