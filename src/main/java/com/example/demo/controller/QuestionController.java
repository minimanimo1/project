package com.example.demo.controller;

import com.example.demo.domain.Ask;
import com.example.demo.domain.Comment;
import com.example.demo.dto.AskRequestDto;
import com.example.demo.dto.AskResponseDto;
import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.AskService;
import com.example.demo.service.CommentService;
import lombok.*;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final AskService askService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @GetMapping(value = "/new")
    public String createForm() {
        return "/questions/write";
    }
    @PostMapping(value="/new/questionList")
    public String create(AskRequestDto requestDto) {
        askService.write(requestDto);
       return "redirect:/questions/questionList";
    }

    @GetMapping(value="/questionList")
    public String list(Model model) {
        List<Ask> asks = askService.findAsks();
        model.addAttribute("asks", asks);
        return "/questions/questionList";
    }

    @GetMapping(value="/{no}")
    public String detail(Model model , @PathVariable("no") Long no) {
        AskResponseDto ask = askService.findAsk(no);
        List<CommentResponseDto> comments = ask.getComments();

      if(comments!=null && !comments.isEmpty()) {
          model.addAttribute("comment", comments);
      }

      model.addAttribute("asks",ask);

      return "/questions/view";
    }
    @GetMapping(value="/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        AskResponseDto ask = askService.findAsk(no);
        model.addAttribute("asks", ask);
        return "/questions/edit";
    }
@PostMapping(value="/new/edit/{no}")
    public String update(@PathVariable("no") Long no, AskRequestDto requestDto) {
        askService.edit(no, requestDto);
        return "redirect:/questions/questionList";
    }


    @PostMapping(value="/delete/{no}")
    public String delete(@PathVariable("no") Long no) {
        askService.delete(no);
        return "redirect:/questions/questionList";
    }

}
