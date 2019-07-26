/*
 * Copyright (C) 2019 Lightbend Inc. <https://www.lightbend.com>
 */

package akka.actor

import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

import akka.dispatch.{ UnboundedControlAwareMessageQueueSemantics, _ }
import com.typesafe.config.Config

//MyNote
//the code here is pretty simple and straightforward
//use this.init() for hard coding the initiation.

private[akka] class Automata {
  var transitions: Vector[Transition] = Vector.empty[Transition]
  var lastTransitions: Vector[Int] = Vector.empty[Int]


  def addTransition(newTransition: Transition): Unit =
    {
      transitions = transitions :+ newTransition
    }

  def addLastTransition(lastTran: Int): Unit =
    {
      lastTransitions = lastTransitions :+ lastTran
    }

  def findPre(inputTransitions: Vector[Transition]): Vector[Transition] =
    {
      var returnVar: Vector[Transition] = Vector.empty[Transition]
      for (inputTransition ← inputTransitions)
        for (transition ← transitions) {
          if (transition.to == inputTransition.from)
            returnVar = returnVar :+ transition
        }
      returnVar
    }

  def findTransitionByMessageBundle(messageBundle: MessageBundle): Vector[Transition] = //returns vector[Transition] of messageBundles, null in case of not found
    {
      var returnVar: Vector[Transition] = Vector.empty[Transition]
      for (transition ← transitions)
        if (transition.messageBundle == messageBundle)
          returnVar = returnVar :+ transition
      returnVar
    }

  def isLastTransition(inputTransition: Transition): Boolean = //returns true if this is one of the lsat transitions, false otherwise
    {
      if (lastTransitions.isEmpty)
        return false
      if (lastTransitions.contains(inputTransition.to))
        return true
      return false
    }

  def init(): Unit =
    {
      //MyNote
      //use addTransition and addLastTransition to initiate the transitions according to algorithm
    }

}

private[akka] case class MessageBundle(sender: ActorRef,message: Any, receiver: ActorCell) {}


private[akka] case class Transition(from: Int, to: Int, messageBundle: MessageBundle, regTransition: Boolean) {

  //MyNote
  //if true, this is regular transition, else, negative transition

}


//MyNote
//TODO shayad bayad ye chizi be in traite ezafe konim, agi kar nakard
//TODO is "private[akka]" right????
//TODO you probably should test it again, so much changes since the last time this was tested.