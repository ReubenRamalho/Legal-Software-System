package com.example.legal_system.infrastructure.persistence.access;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.dto.AccessRecordDTO;
import com.example.legal_system.model.Access;

/**
 * JPA-based implementation of {@link IAccessRepository}.
 *
 * <p>Adapts the Spring Data JPA query (which works with {@link LocalDateTime})
 * to the domain interface (which accepts {@link LocalDate}), mapping the date
 * boundaries to the start and end of the respective days.</p>
 */
@Repository
public class AccessRepositoryImpl implements IAccessRepository {

    private final AccessJpaRepository accessJpaRepository;

    public AccessRepositoryImpl(AccessJpaRepository accessJpaRepository) {
        this.accessJpaRepository = accessJpaRepository;
    }

    @Override
    public List<AccessRecordDTO> findByHourDateAccessBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        List<Access> records = accessJpaRepository.findByHourDateAccessBetween(start, end);

        return records.stream()
                .map(record -> new AccessRecordDTO(
                        record.getUserId(),
                        record.getHourDateAccess(),
                        record.getVisitedPage()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Access access) {
        accessJpaRepository.save(access);
    }
}
