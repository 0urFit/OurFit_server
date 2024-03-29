package project1.OurFit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class ExerciseRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String routineName;
    private String imgpath;
    private int daysPerWeek;
    private int level;
    private int programLength;
    private String lastDay;

    @OneToMany(mappedBy = "exerciseRoutine")
    List<ExerciseEnroll> exerciseEnrollList = new ArrayList<>();

    @OneToMany(mappedBy = "exerciseRoutine")
    List<ExerciseDetail> exerciseDetailList = new ArrayList<>();

    @OneToMany(mappedBy = "exerciseRoutine")
    List<ExerciseLike> exerciseLikeList = new ArrayList<>();
}
