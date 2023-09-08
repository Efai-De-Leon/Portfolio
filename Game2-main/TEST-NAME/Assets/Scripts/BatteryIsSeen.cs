using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BatteryIsSeen : SeenByPlayer
{
    public override bool AnimatorControllerExists()
    {
        SetAnimatorController(GetComponent<Animator>());
        return true;
    }
    public override void PlayAnimationWhenSeen()
    {
        GetAnimatorController().SetBool("Is_seen", true);
    } 
}
