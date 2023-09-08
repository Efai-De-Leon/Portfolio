using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.VFX;
public class Laser : MonoBehaviour
{
    public void SetLaserActive()
    {
        gameObject.SetActive(true);
        gameObject.GetComponent<VisualEffect>().Play();
    }

    public void KillLaser()
    {
        gameObject.SetActive(false);
        gameObject.GetComponent<VisualEffect>().Stop();
        gameObject.transform.position = gameObject.transform.parent.position;
    }
    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.tag == "Enemy")
        {
            Debug.Log("Enemy hit");
            collision.gameObject.GetComponent<NeedleEnemy>().KillEnemy();
        }
        KillLaser();
    }
}
