using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.VFX;
public class ShootingLaser : MonoBehaviour
{
    [SerializeField] private GameObject laserPrefab;
    [SerializeField] private GameObject explosionPrefab;
    private VisualEffect explosionPrefabVFX;
    private Laser laserScript; 
    bool isShooting = false;
    Vector3 laserDirection;
    Quaternion laserRotation;

    // Start is called before the first frame update
    void Start()
    {
        laserPrefab.transform.position = transform.position;
        laserScript = laserPrefab.GetComponent<Laser>();
        explosionPrefabVFX = explosionPrefab.GetComponent<VisualEffect>();
    }
    // Update is called once per frame
    void Update()
    {
        if(Input.GetButtonDown("Jump") && !isShooting && (transform.rotation.eulerAngles.y ==  90 || transform.rotation.eulerAngles.y == 270))
        {
            PlayExplosion();
            Debug.Log("Space is pressed");
            SetRotationOfLaser();
            laserScript.SetLaserActive();
            isShooting = true;
        }
        MoveLaser();
        StopLaser();
    }

    void MoveLaser()
    {
        if (isShooting)
        {
            laserPrefab.transform.position += laserDirection * Time.deltaTime * 25;
        }
    }

    void StopLaser()
    {
        if(Vector3.Distance(laserPrefab.transform.position, transform.position) > 10)
        {
            laserScript.KillLaser();
            isShooting = false;
        }
    }

    void PlayExplosion()
    {
        explosionPrefab.SetActive(true);
        explosionPrefabVFX.Play();
    }
    void StopExplosion()
    {
        explosionPrefabVFX.Stop();
        explosionPrefab.SetActive(false);
    }

    void SetLaserStartPosition(int offset)
    {
        Vector3 laserStartPosition = transform.position;
        laserStartPosition.x += offset;
        laserPrefab.transform.position = laserStartPosition;
    }

    void SetRotationOfLaser()
    {       
        if(transform.rotation.eulerAngles.y == 90)
        {
            SetLaserStartPosition(1);
            laserRotation.eulerAngles = new Vector3(0, 180, 0);
            laserDirection = Vector3.left;
        }
        if(transform.rotation.eulerAngles.y == 270)
        {
            SetLaserStartPosition(-1);
            laserRotation.eulerAngles = new Vector3(0, 0, 0);
            laserDirection = Vector3.right;
        }
        laserPrefab.transform.rotation = laserRotation;
    }
}
