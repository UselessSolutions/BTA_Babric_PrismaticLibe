package useless.prismaticlibe;

// Items that implement IColored will use the colored item renderer
public interface IColored {
    int baseColor(); // 24bit color represent by an integer
    int overlayColor(); // 24bit color represent by an integer

    int[] baseTexture(); // Texture coordinates
    int[] overlayTexture(); // Texture coordinates
}
