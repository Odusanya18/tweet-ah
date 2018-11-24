package com.twitter.tweetah.controller;

import com.twitter.tweetah.model.Tweet;
import com.twitter.tweetah.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class TweetController {
    @Autowired
    private TweetRepository tweetRepository;

    @GetMapping("/tweets")
    public Flux<Tweet> getAllTweets(){
        return tweetRepository.findAll();
    }

    @PostMapping("/tweets")
    public Mono<Tweet> createTweet(@Valid @RequestBody Tweet tweet){
        return tweetRepository.save(tweet);
    }

    @GetMapping("/tweets/{id}")
    public Mono<ResponseEntity<Tweet>> getTweetById(@PathVariable(value = "id") String id){
        return tweetRepository.findById(id)
                .map(savedTweet -> ResponseEntity.ok(savedTweet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/tweets/{id}")
    public Mono<ResponseEntity<Tweet>> updateTweet(@PathVariable(value = "id") String id, @Valid @RequestBody Tweet tweet){
        return tweetRepository.findById(id)
                .flatMap(existingTweet -> {
                    existingTweet.setText(tweet.getText());
                    return tweetRepository.save(existingTweet);
                })
                .map(updatedTweet -> new ResponseEntity<>(updatedTweet, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/tweets/{id}")
    public Mono<ResponseEntity<Void>> deleteTweet(@PathVariable(value = "id") String id){
        return tweetRepository.findById(id)
                .flatMap(tweet -> tweetRepository.delete(tweet)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> streamAllTweets(){
        return tweetRepository.findAll();
    }
}
