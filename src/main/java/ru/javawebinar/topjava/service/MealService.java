package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;


@Service
public class MealService {

    private MealRepository repository;
@Autowired
    public MealService(MealRepository repository) {
    this.repository = repository;
    }

    Meal save(Meal meal, int userId){
  repository.save(meal,userId);
    }

    // false if meal do not belong to userId
    boolean delete(int id, int userId)throws NotFoundException{
     checkNotFoundWithId(repository.delete(id,userId), id);
    }

    // null if meal do not belong to userId
    Meal get(int id, int userId){
       return checkNotFoundWithId(repository.get(id,userId), id);
    }

    // ORDERED dateTime desc
    List<Meal> getAll(int userId){
 return repository.getAll(userId);
    }

    // ORDERED dateTime desc

    public List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetween(
                DateTimeUtil.createDateTime(startDate, LocalDate.MIN, LocalTime.MIN),
                DateTimeUtil.createDateTime(endDate, LocalDate.MAX, LocalTime.MAX),
                userId);
        return repository.getBetweenInclusive(startDate, endDate, userId);
    }

}