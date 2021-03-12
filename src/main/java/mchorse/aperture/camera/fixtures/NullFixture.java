package mchorse.aperture.camera.fixtures;

import mchorse.aperture.camera.CameraProfile;
import mchorse.aperture.camera.data.Position;
import mchorse.aperture.camera.modifiers.AbstractModifier;
import mchorse.mclib.config.values.ValueBoolean;

public class NullFixture extends AbstractFixture
{
    public ValueBoolean previous = new ValueBoolean("previous", false);

    public NullFixture(long duration)
    {
        super(duration);

        this.register(this.previous);
    }

    @Override
    public void applyFixture(long ticks, float partialTick, float previewPartialTick, CameraProfile profile, Position pos)
    {
        int index = profile.fixtures.indexOf(this);

        if (index != -1)
        {
            boolean previous = this.previous.get();
            AbstractFixture fixture = profile.get(index + (previous ? -1 : 1));

            if (fixture == null || fixture instanceof NullFixture)
            {
                return;
            }

            long target = previous ? fixture.getDuration() : 0;
            long offset = profile.calculateOffset(fixture);

            fixture.applyFixture(target, 0, 0, profile, pos);
            AbstractModifier.applyModifiers(profile, fixture, offset, target, 0, 0, pos);
        }
    }

    @Override
    public AbstractFixture create(long duration)
    {
        return new NullFixture(duration);
    }
}