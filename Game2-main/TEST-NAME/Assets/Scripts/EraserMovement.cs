using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.VFX;
public class EraserMovement : MonoBehaviour
{
    [SerializeField] TrailRenderer eraserTrail;
    [SerializeField] TrailRenderer eraserTrail2;
    private Rigidbody eraserRb;
    private float rotationSpeed = 40f;
    void Start()
    {
        gameObject.SetActive(false);
        eraserRb = GetComponent<Rigidbody>();
    }
    public void LaunchEraser()
    {
        gameObject.transform.rotation = Quaternion.Euler(90, 0, 0);
        gameObject.SetActive(true); 
        eraserRb.AddTorque(new Vector3(1, 0, 0) * rotationSpeed);
    }

    public void KillEraser()
    {
        eraserTrail.Clear();
        eraserTrail2.Clear();
        gameObject.SetActive(false);
        gameObject.transform.position = gameObject.transform.parent.position;
        eraserRb.velocity = Vector3.zero;
        eraserRb.angularVelocity = Vector3.zero;
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.tag == "Enemy")
        {
            Debug.Log("Enemy hit");
            collision.gameObject.GetComponent<EnemyNeedleMovementAI>().SetStop(true);
            collision.gameObject.GetComponent<NeedleEnemy>().KillEnemy();
        }
        KillEraser();
    }
}
