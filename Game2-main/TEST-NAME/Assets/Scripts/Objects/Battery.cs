using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Battery : MonoBehaviour
{
    private Pool particleSpawner;
    // Start is called before the first frame update
    
    void Start()
    {
        //may need to fix
        particleSpawner = GameObject.FindGameObjectWithTag("BatteryParticleSpawner").GetComponent<Pool>();
    }

    // Update is called once per frame
    void Update()
    {
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            ParticleSystem particle = particleSpawner.GetObject().GetComponent<ParticleSystem>();
            particle.transform.position = transform.position;
            particle.Play();
        }
    }
}
