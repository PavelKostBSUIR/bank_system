package com.vojtechruzicka.javafxweaverexample.backController;

import com.vojtechruzicka.javafxweaverexample.service.EndBankDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Date;


@Controller
public class EndBankDayController {
    private final EndBankDayService endBankDayService;

    @Autowired
    public EndBankDayController(EndBankDayService endBankDayService) {
        this.endBankDayService = endBankDayService;
    }

    public Date endBankDay(Date currentDate) {
        return endBankDayService.endBankDay(currentDate);
    }
}
