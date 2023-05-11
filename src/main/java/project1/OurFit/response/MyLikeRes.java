package project1.OurFit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project1.OurFit.entity.ExerciseRoutine;
@Getter
@AllArgsConstructor
public class MyLikeRes {
    private String routineName;
    private int level;
    private int fewTime;
    private int period;

    public MyLikeRes(ExerciseRoutine ex){
        this.routineName=ex.getRoutineName();
        this.level=ex.getLevel();
        this.fewTime=ex.getFewTime();
        this.period=ex.getPeriod();
    }
}


