using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ParticlePoolBatteryExplosion : ParticlePool
{
    protected override void SetParticleSpawner()
    {
        SetParticleSapwnerTag("BatteryParticleSpawner");
    }
}
