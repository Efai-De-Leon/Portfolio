using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ParticlePoolString : ParticlePool
{
    protected override void SetParticleSpawner()
    {
        SetParticleSapwnerTag("CottonParticleSpawner");
    }
}
