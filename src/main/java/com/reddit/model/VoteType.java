package com.reddit.model;

public enum VoteType  {
    UPVOTE(1),
    DOWNVOTE(-1),;


    public  int direction;


     VoteType(int direction) {

     }

     public int getDisplay() {
         return direction;
     }

//     public static VoteType lookup(Integer direction) {
//        return Arrays.stream(VoteType.values())
//                .filter(voteType -> voteType.)
//     }
//    public static VoteType lookup(int direction){
//        return Arrays.stream(VoteType.values())
//                .filter(value -> value.getDisplay().equals(direction))
//                .findAny()
//                .orElseThrow(()-> new SpringRedditException("vote not found"));
//    }
}
