package useless.prismaticlibe;

// Items that implement IColored will use the colored item renderer
public interface IColored {
    public int baseColor(); // 24bit color represent by an integer
    public int overlayColor(); // 24bit color represent by an integer

    public int[] baseTexture(); // Texture coordinates
    public int[] overlayTexture(); // Texture coordinates
}
