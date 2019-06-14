package com.chakans.portal.repository.timezone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chakans.portal.domain.timezone.DateTimeWrapper;

/**
 * Spring Data JPA repository for the {@link DateTimeWrapper} entity.
 */
@Repository
public interface DateTimeWrapperRepository extends JpaRepository<DateTimeWrapper, Long> {

}
