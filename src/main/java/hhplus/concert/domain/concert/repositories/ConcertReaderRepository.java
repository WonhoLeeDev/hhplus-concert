package hhplus.concert.domain.concert.repositories;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;

import java.util.List;

public interface ConcertReaderRepository {

    List<Concert> getConcerts();

    Concert getConcertById(Long id);

    ConcertOption getConcertOptionById(Long id);
}