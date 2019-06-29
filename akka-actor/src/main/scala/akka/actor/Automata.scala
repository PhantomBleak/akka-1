/*
 * Copyright (C) 2019 Lightbend Inc. <https://www.lightbend.com>
 */

package akka.actor

import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

import akka.dispatch.{ UnboundedControlAwareMessageQueueSemantics, _ }
import com.typesafe.config.Config

private[akka] class Automata {
  var transitions: Vector[Transition] = Vector.empty[Transition]
  var lastTransitions: Vector[Int] = Vector.empty[Int]

  //TODO test the syntax : transitions = transitions :+ newTransition and lastTransitions = lastTransitions :+ lastTran
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
      //use addTransition and addLastTransition to initiate the transitions according to algorithm
    }

}

//final case class MessageBundle private (val sender: ActorRef, val message: Any, val receiver: ActorRef)

//TODO check this out, test it
//changed it back - testing : compiled fine : 3/30/2019
private[akka] case class MessageBundle(val sender: ActorRef, val message: Any, val receiver: ActorCell) {}

/*object MessageBundle {
  def apply(message: Any, sender: ActorRef, system: ActorSystem): Envelope = {
    if (message == null) throw InvalidMessageException("Message is null")
    new Envelope(message, if (sender ne Actor.noSender) sender else system.deadLetters)
  }
}*/

private[akka] case class Transition(val from: Int, val to: Int, val messageBundle: MessageBundle, val regTransition: Boolean) {

  //if true, this is regular transition, else, negative transition

}

//TODO shayad bayad ye chizi be in traite ezafe konim, agi kar nakard
//az khatte 808 be bad tooye Mailboc.scala ide begir agi OK nabood

//TODO : bar rasi kon jaryane ControlMessage too khatte 835 ro ide begiri extend MyControlMessage for handling messages for this shit!
//TODO test this shit ASAP
//TODO is "private[akka]" right?
//TODO avvla bayad configFile ro peyda koni, besazi ya harchi
//TODO ye teste doros hesabi bokon, avval config, badesh bebin mishe dorost az in mailboxi ke sakhti estefade kard ya na?

//TODO add a second file for mailbox

//compiled ok, 3/30/2019
//baraye classayi ke tarif kardam : shayad bayad injoori bashan, dont know yet
