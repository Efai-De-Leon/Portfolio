using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SeenByPlayer : MonoBehaviour
{
    bool seenByPayer = false;
    private Animator animatorController;
    private GameObject Player;
    // Start is called before the first frame update
    void Start()
    {
        Player = GameObject.FindWithTag("Player");
    }

    // Update is called once per frame
    void Update()
    {
        if (!seenByPayer && GetDistanceToPlayer() < 5.0f)
        {
            SetObjectOnTopOfCotton();
            if(AnimatorControllerExists())
                PlayAnimationWhenSeen();
            seenByPayer = true;
        }
    }

    void SetObjectOnTopOfCotton()
    {
        transform.position = new Vector3(transform.position.x, transform.position.y, transform.position.z + 1.0f);
        gameObject.GetComponent<MeshRenderer>().enabled = true;
    }

    float GetDistanceToPlayer() 
    {
        return Vector3.Distance(transform.position, Player.transform.position);
    }

    public bool GetSeenByPlayer()
    {
        return seenByPayer;
    }

    public virtual bool AnimatorControllerExists()
    {
        return false;
    }

    public virtual void PlayAnimationWhenSeen()
    {
    }

    public Animator GetAnimatorController()
    {
        return animatorController;
    }

    public void SetAnimatorController(Animator animatorController)
    {
        this.animatorController = animatorController;
    }

}
