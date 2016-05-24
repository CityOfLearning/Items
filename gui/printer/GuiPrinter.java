package com.dyn.item.gui.printer;

import net.minecraft.client.gui.GuiScreen;

public class GuiPrinter extends GuiScreen {
	/*
	 * private static final ResourceLocation texture = new
	 * ResourceLocation("printerblock", "textures/gui/printerblock.png"); public
	 * int mouseX; public int mouseY;
	 *
	 * public GuiPrinter(World world, int i, int j, int k, int l, int d) {
	 * this.worldObj = world; this.posX = i; this.posY = j; this.posZ = k;
	 * this.mData = l; this.direction = d; this.color = 0; this.brushSize = 1;
	 * this.currentLayer = 0; this.drawingOptions = 0; this.lastSave = 0;
	 * this.field_353_b = 0; this.visitedLayer[0] = true; if (this.direction >
	 * 0) { for (int layer = 0; layer < 100; layer++) { for (int l1 = 0; l1 <
	 * this.yLength; l1++) { for (int l2 = 0; l2 < this.xLength; l2++) { if
	 * ((this.mData == 2) || (this.mData == 7)) { if
	 * (this.worldObj.getBlock(this.posX - l2 * this.direction, this.posY +
	 * (this.yLength - 1) - l1, this.posZ + layer) != Blocks.wool) {
	 * this.blocks[layer][l1][l2] = 16; } else { this.blocks[layer][l1][l2] =
	 * this.worldObj.getBlockMetadata(this.posX - l2 * this.direction, this.posY
	 * + (this.yLength - 1) - l1, this.posZ + layer); } } else if ((this.mData
	 * == 3) || (this.mData == 8)) { if (this.worldObj.getBlock(this.posX + l2 *
	 * this.direction, this.posY + (this.yLength - 1) - l1, this.posZ - layer)
	 * != Blocks.wool) { this.blocks[layer][l1][l2] = 16; } else {
	 * this.blocks[layer][l1][l2] = this.worldObj.getBlockMetadata(this.posX +
	 * l2 * this.direction, this.posY + (this.yLength - 1) - l1, this.posZ -
	 * layer); } } else if ((this.mData == 4) || (this.mData == 9)) { if
	 * (this.worldObj.getBlock(this.posX + layer, this.posY + (this.yLength - 1)
	 * - l1, this.posZ + l2 * this.direction) != Blocks.wool) {
	 * this.blocks[layer][l1][l2] = 16; } else { this.blocks[layer][l1][l2] =
	 * this.worldObj.getBlockMetadata(this.posX + layer, this.posY +
	 * (this.yLength - 1) - l1, this.posZ + l2 * this.direction); } } else if
	 * ((this.mData == 5) || (this.mData == 10)) { if
	 * (this.worldObj.getBlock(this.posX - layer, this.posY + (this.yLength - 1)
	 * - l1, this.posZ - l2 * this.direction) != Blocks.wool) {
	 * this.blocks[layer][l1][l2] = 16; } else { this.blocks[layer][l1][l2] =
	 * this.worldObj.getBlockMetadata(this.posX - layer, this.posY +
	 * (this.yLength - 1) - l1, this.posZ - l2 * this.direction); } } } } } }
	 * else { for (int layer = 0; layer < 100; layer++) { for (int l1 = 0; l1 <
	 * this.yLength; l1++) { for (int l2 = 0; l2 < this.xLength; l2++) { if
	 * ((this.mData == 2) || (this.mData == 7)) { if
	 * (this.worldObj.getBlock(this.posX - l2 * this.direction, this.posY +
	 * (this.yLength - 1) - l1, this.posZ + layer) != Blocks.wool) {
	 * this.blocks[layer][l1][(this.xLength - l2 - 1)] = 16; } else {
	 * this.blocks[layer][l1][(this.xLength - l2 - 1)] =
	 * this.worldObj.getBlockMetadata(this.posX - l2 * this.direction, this.posY
	 * + (this.yLength - 1) - l1, this.posZ + layer); } } else if ((this.mData
	 * == 3) || (this.mData == 8)) { if (this.worldObj.getBlock(this.posX + l2 *
	 * this.direction, this.posY + (this.yLength - 1) - l1, this.posZ - layer)
	 * != Blocks.wool) { this.blocks[layer][l1][(this.xLength - l2 - 1)] = 16; }
	 * else { this.blocks[layer][l1][(this.xLength - l2 - 1)] =
	 * this.worldObj.getBlockMetadata(this.posX + l2 * this.direction, this.posY
	 * + (this.yLength - 1) - l1, this.posZ - layer); } } else if ((this.mData
	 * == 4) || (this.mData == 9)) { if (this.worldObj.getBlock(this.posX +
	 * layer, this.posY + (this.yLength - 1) - l1, this.posZ + l2 *
	 * this.direction) != Blocks.wool) { this.blocks[layer][l1][(this.xLength -
	 * l2 - 1)] = 16; } else { this.blocks[layer][l1][(this.xLength - l2 - 1)] =
	 * this.worldObj.getBlockMetadata(this.posX + layer, this.posY +
	 * (this.yLength - 1) - l1, this.posZ + l2 * this.direction); } } else if
	 * ((this.mData == 5) || (this.mData == 10)) { if
	 * (this.worldObj.getBlock(this.posX - layer, this.posY + (this.yLength - 1)
	 * - l1, this.posZ - l2 * this.direction) != Blocks.wool) {
	 * this.blocks[layer][l1][(this.xLength - l2 - 1)] = 16; } else {
	 * this.blocks[layer][l1][(this.xLength - l2 - 1)] =
	 * this.worldObj.getBlockMetadata(this.posX - layer, this.posY +
	 * (this.yLength - 1) - l1, this.posZ - l2 * this.direction); } } } } } } }
	 *
	 * public void initGui() { this.buttonList.clear(); int i1 = (this.width -
	 * this.xSize) / 2; int j1 = (this.height - this.ySize) / 4;
	 * this.buttonList.add(new GuiButton(1, i1 - 52, j1 + 18, 50, 20, "Print"));
	 * this.buttonList.add(new GuiButton(2, i1 - 52, j1 + 43, 50, 20, "Clear"));
	 * this.buttonList.add(new GuiButton(4, i1 - 48, j1 + 119, 20, 20, "-"));
	 * this.buttonList.add(new GuiButton(5, i1 - 27, j1 + 119, 20, 20, "+"));
	 * this.buttonList.add(new GuiButton(6, i1 - 48, j1 + 180, 20, 20, "-"));
	 * this.buttonList.add(new GuiButton(7, i1 - 27, j1 + 180, 20, 20, "+"));
	 * this.buttonList.add(new GuiButton(8, i1 - 48, j1 + 201, 20, 20, "--"));
	 * this.buttonList.add(new GuiButton(9, i1 - 27, j1 + 201, 20, 20, "++"));
	 * this.buttonList.add(new GuiButton(10, i1 + this.xSize + this.xSize2 + 8,
	 * j1 + 50, 50, 20, "Copy")); this.buttonList.add(new GuiButton(11, i1 +
	 * this.xSize + this.xSize2 + 8, j1 + 75, 50, 20, "Paste")); if
	 * (this.drawingOptions == 0) { this.buttonList.add(new GuiButton(12, i1 -
	 * 52, j1 + 68, 50, 20, "Replace")); } else { this.buttonList.add(new
	 * GuiButton(13, i1 - 52, j1 + 68, 50, 20, "Draw")); }
	 * this.buttonList.add(new GuiButton(14, i1 + this.xSize + this.xSize2 + 8,
	 * j1 + 100, 50, 20, "Undo")); this.buttonList.add(new GuiButton(15, i1 +
	 * this.xSize + this.xSize2 + 8, j1 + 125, 50, 20, "Redo"));
	 * this.buttonList.add(new GuiButton(16, i1 + this.xSize + this.xSize2 + 8,
	 * j1 + 150, 50, 20, "Rectangle")); this.buttonList.add(new GuiButton(17, i1
	 * + this.xSize + this.xSize2 + 8, j1 + 175, 50, 20, "Fill")); }
	 *
	 * protected void actionPerformed(GuiButton guibutton) { switch
	 * (guibutton.id) { case 1: print(); this.mc.displayGuiScreen(null); break;
	 * case 2: save(); for (int l1 = 0; l1 < this.yLength; l1++) { for (int l2 =
	 * 0; l2 < this.xLength; l2++) { this.blocks[this.currentLayer][l1][l2] =
	 * 16; } } break; case 4: if (this.brushSize > 1) { this.brushSize -= 1; }
	 * break; case 5: if (this.brushSize < 5) { this.brushSize += 1; } break;
	 * case 6: if (this.currentLayer > 0) { this.currentLayer -= 1; } break;
	 * case 7: if (this.currentLayer < 99) { this.currentLayer += 1; } break;
	 * case 8: if (this.currentLayer > 0) { this.currentLayer -= 10; } if
	 * (this.currentLayer < 0) { this.currentLayer = 0; } break; case 9: if
	 * (this.currentLayer < 99) { this.currentLayer += 10; } if
	 * (this.currentLayer > 99) { this.currentLayer = 99; } break; case 10:
	 * copy(); break; case 11: paste(); break; case 12: this.drawingOptions = 1;
	 * initGui(); break; case 13: this.drawingOptions = 0; initGui(); break;
	 * case 14: if (this.lastSave > 0) { this.lastSave -= 1; for (int layer = 0;
	 * layer < 100; layer++) { for (int l1 = 0; l1 < this.yLength; l1++) { for
	 * (int l2 = 0; l2 < this.xLength; l2++) {
	 * this.redo[this.lastSave][layer][l1][l2] = this.blocks[layer][l1][l2];
	 * this.blocks[layer][l1][l2] = this.save[this.lastSave][layer][l1][l2]; } }
	 * } this.hasUndone = true; } break; case 15: if (this.field_353_b >
	 * this.lastSave) { for (int layer = 0; layer < 100; layer++) { for (int l1
	 * = 0; l1 < this.yLength; l1++) { for (int l2 = 0; l2 < this.xLength; l2++)
	 * { this.blocks[layer][l1][l2] = this.redo[this.lastSave][layer][l1][l2]; }
	 * } } this.lastSave += 1; } break; case 16: this.drawingOptions = 2;
	 * initGui(); break; case 17: this.drawingOptions = 4; initGui(); } }
	 *
	 * private void print() { try { for (int layer = 0; layer < 100; layer++) {
	 * if (this.visitedLayer[layer]) { ByteArrayOutputStream bytes = new
	 * ByteArrayOutputStream(); DataOutputStream data = new
	 * DataOutputStream(bytes); int[] dataInt = {
	 * this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ,
	 * this.mData, this.direction }; for (int i = 0; i < dataInt.length; i++) {
	 * data.writeInt(dataInt[i]); } data.writeInt(layer); for (int l1 = 0; l1 <
	 * this.yLength; l1++) { for (int l2 = 0; l2 < this.xLength; l2++) {
	 * data.writeInt(this.blocks[layer][l1][l2]); } } FMLProxyPacket pkt = new
	 * FMLProxyPacket((PacketBuffer)
	 * Unpooled.wrappedBuffer(bytes.toByteArray()), "PrinterBlock");
	 * ItemMod.channel.sendToServer(pkt); } } } catch (IOException e) {} }
	 *
	 * protected void mouseClicked(int i, int j, int k) throws IOException {
	 * super.mouseClicked(i, j, k); int i1 = (this.width - this.xSize) / 2; int
	 * j1 = (this.height - this.ySize) / 4; int i2 = 5; int j2 = 5; for (int l1
	 * = 0; l1 < this.yLength; l1++) { for (int l2 = 0; l2 < this.xLength; l2++)
	 * { if ((k == 0) && (i >= i1 + i2 + l2 * this.colorSize) && (i < i1 + i2 +
	 * this.colorSize + l2 * this.colorSize) && (j >= j1 + j2 + l1 *
	 * this.colorSize) && (j < j1 + j2 + this.colorSize + l1 * this.colorSize))
	 * { save(); if (this.drawingOptions == 0) { for (int l3 = -this.brushSize +
	 * 1; l3 < this.brushSize; l3++) { for (int l4 = -this.brushSize + 1; l4 <
	 * this.brushSize; l4++) { if ((l2 + l4 >= 0) && (l2 + l4 < this.xLength) &&
	 * (l1 + l3 >= 0) && (l1 + l3 < this.yLength)) {
	 * this.blocks[this.currentLayer][(l1 + l3)][(l2 + l4)] = this.color;
	 * this.visitedLayer[this.currentLayer] = true; } } } } else if
	 * (this.drawingOptions == 1) { int c =
	 * this.blocks[this.currentLayer][l1][l2]; for (int l3 = 0; l3 <
	 * this.yLength; l3++) { for (int l4 = 0; l4 < this.xLength; l4++) { if
	 * (this.blocks[this.currentLayer][l3][l4] == c) {
	 * this.blocks[this.currentLayer][l3][l4] = this.color; }
	 * this.blocks[this.currentLayer][l1][l2] = this.color;
	 * this.visitedLayer[this.currentLayer] = true; } } } else if
	 * (this.drawingOptions == 2) { this.linePosX = l2; this.linePosY = l1;
	 * this.drawingOptions = 3; } else if (this.drawingOptions == 3) { if (l1 >=
	 * this.linePosY) { if (l2 >= this.linePosX) { for (int l3 = 0; l3 < l1 -
	 * this.linePosY + 1; l3++) { for (int l4 = 0; l4 < l2 - this.linePosX + 1;
	 * l4++) { this.blocks[this.currentLayer][(this.linePosY +
	 * l3)][(this.linePosX + l4)] = this.color;
	 * this.visitedLayer[this.currentLayer] = true; } } } else { for (int l3 =
	 * 0; l3 < l1 - this.linePosY + 1; l3++) { for (int l4 = 0; l4 > l2 -
	 * this.linePosX - 1; l4--) { this.blocks[this.currentLayer][(this.linePosY
	 * + l3)][(this.linePosX + l4)] = this.color;
	 * this.visitedLayer[this.currentLayer] = true; } } } } else if (l2 >=
	 * this.linePosX) { for (int l3 = 0; l3 > l1 - this.linePosY - 1; l3--) {
	 * for (int l4 = 0; l4 < l2 - this.linePosX + 1; l4++) {
	 * this.blocks[this.currentLayer][(this.linePosY + l3)][(this.linePosX +
	 * l4)] = this.color; this.visitedLayer[this.currentLayer] = true; } } }
	 * else { for (int l3 = 0; l3 > l1 - this.linePosY - 1; l3--) { for (int l4
	 * = 0; l4 > l2 - this.linePosX - 1; l4--) {
	 * this.blocks[this.currentLayer][(this.linePosY + l3)][(this.linePosX +
	 * l4)] = this.color; this.visitedLayer[this.currentLayer] = true; } } }
	 * this.drawingOptions = 2; } if ((this.drawingOptions == 4) &&
	 * (this.blocks[this.currentLayer][l1][l2] != this.color)) { fillArea(l2,
	 * l1, this.blocks[this.currentLayer][l1][l2]);
	 * this.visitedLayer[this.currentLayer] = true; } } } } if ((k == 0) && (i >
	 * i1 + this.xSize + 13) && (i < i1 + this.xSize + 13 + 10) && (j > j1 + 35)
	 * && (j < j1 + 35 + 10)) { this.color = 16; } for (int l = 0; l < 16; l++)
	 * { if ((k == 0) && (i > i1 + this.xSize + 13) && (i < i1 + this.xSize + 13
	 * + 10) && (j > j1 + 36 + 10 + l * 11) && (j < j1 + 36 + 10 + 10 + l * 11))
	 * { this.color = l; } } }
	 *
	 * protected void mouseMovedOrUp(int i, int j, int k) {
	 * super.mouseMovedOrUp(i, j, k);
	 *
	 * this.mouseX = i; this.mouseY = j; }
	 *
	 * public void drawScreen(int i, int j, float f) { GL11.glColor4f(1.0F,
	 * 1.0F, 1.0F, 1.0F); this.mc.renderEngine.bindTexture(texture); int i1 =
	 * (this.width - this.xSize) / 2; int j1 = (this.height - this.ySize) / 4;
	 * drawTexturedModalRect(i1, j1, 0, 0, this.xSize, this.ySize); int i2 = 5;
	 * int j2 = 5; for (int l1 = 0; l1 < this.yLength; l1++) { for (int l2 = 0;
	 * l2 < this.xLength; l2++) { boolean flag = false; for (int l3 = 0; l3 <
	 * 17; l3++) { if (this.blocks[this.currentLayer][l1][l2] == l3) {
	 * drawTexturedModalRect(i1 + i2 + l2 * this.colorSize, j1 + j2 + l1 *
	 * this.colorSize, this.colorSize * l3, this.ySize + 10, this.colorSize,
	 * this.colorSize); flag = true; } if ((!flag) && (this.currentLayer > 0) &&
	 * (this.blocks[(this.currentLayer - 1)][l1][l2] == l3)) {
	 * drawTexturedModalRect(i1 + i2 + l2 * this.colorSize, j1 + j2 + l1 *
	 * this.colorSize, this.colorSize * l3, this.ySize + 15, this.colorSize,
	 * this.colorSize); } } } } for (int l1 = 0; l1 < this.yLength; l1++) { for
	 * (int l2 = 0; l2 < this.xLength; l2++) { if ((this.drawingOptions == 0) &&
	 * (this.mouseX >= i1 + i2 + l2 * this.colorSize) && (this.mouseX < i1 + i2
	 * + this.colorSize + l2 * this.colorSize) && (this.mouseY >= j1 + j2 + l1 *
	 * this.colorSize) && (this.mouseY < j1 + j2 + this.colorSize + l1 *
	 * this.colorSize)) { for (int l3 = -this.brushSize + 1; l3 <
	 * this.brushSize; l3++) { for (int l4 = -this.brushSize + 1; l4 <
	 * this.brushSize; l4++) { if ((l2 + l4 >= 0) && (l2 + l4 < this.xLength) &&
	 * (l1 + l3 >= 0) && (l1 + l3 < this.yLength)) { drawTexturedModalRect(i1 +
	 * i2 + l2 * this.colorSize + l4 * this.colorSize, j1 + j2 + l1 *
	 * this.colorSize + l3 * this.colorSize, this.color * this.colorSize,
	 * this.ySize + 10, this.colorSize, this.colorSize);
	 * drawTexturedModalRect(i1 + i2 + l2 * this.colorSize + l4 *
	 * this.colorSize, j1 + j2 + l1 * this.colorSize + l3 * this.colorSize,
	 * this.colorSize * 16, this.ySize + 10, this.colorSize, this.colorSize); }
	 * } } } else if ((this.drawingOptions == 3) && (this.mouseX >= i1 + i2 + l2
	 * * this.colorSize) && (this.mouseX < i1 + i2 + this.colorSize + l2 *
	 * this.colorSize) && (this.mouseY >= j1 + j2 + l1 * this.colorSize) &&
	 * (this.mouseY < j1 + j2 + this.colorSize + l1 * this.colorSize)) { if (l1
	 * >= this.linePosY) { if (l2 >= this.linePosX) { for (int l3 = 0; l3 < l1 -
	 * this.linePosY + 1; l3++) { for (int l4 = 0; l4 < l2 - this.linePosX + 1;
	 * l4++) { drawTexturedModalRect(i1 + i2 + this.linePosX * this.colorSize +
	 * l4 * this.colorSize, j1 + j2 + this.linePosY * this.colorSize + l3 *
	 * this.colorSize, this.color * this.colorSize, this.ySize + 10,
	 * this.colorSize, this.colorSize); drawTexturedModalRect(i1 + i2 +
	 * this.linePosX * this.colorSize + l4 * this.colorSize, j1 + j2 +
	 * this.linePosY * this.colorSize + l3 * this.colorSize, this.colorSize *
	 * 16, this.ySize + 10, this.colorSize, this.colorSize); } } } else { for
	 * (int l3 = 0; l3 < l1 - this.linePosY + 1; l3++) { for (int l4 = 0; l4 >
	 * l2 - this.linePosX - 1; l4--) { drawTexturedModalRect(i1 + i2 +
	 * this.linePosX * this.colorSize + l4 * this.colorSize, j1 + j2 +
	 * this.linePosY * this.colorSize + l3 * this.colorSize, this.color *
	 * this.colorSize, this.ySize + 10, this.colorSize, this.colorSize);
	 * drawTexturedModalRect(i1 + i2 + this.linePosX * this.colorSize + l4 *
	 * this.colorSize, j1 + j2 + this.linePosY * this.colorSize + l3 *
	 * this.colorSize, this.colorSize * 16, this.ySize + 10, this.colorSize,
	 * this.colorSize); } } } } else if (l2 >= this.linePosX) { for (int l3 = 0;
	 * l3 > l1 - this.linePosY - 1; l3--) { for (int l4 = 0; l4 < l2 -
	 * this.linePosX + 1; l4++) { drawTexturedModalRect(i1 + i2 + this.linePosX
	 * * this.colorSize + l4 * this.colorSize, j1 + j2 + this.linePosY *
	 * this.colorSize + l3 * this.colorSize, this.color * this.colorSize,
	 * this.ySize + 10, this.colorSize, this.colorSize);
	 * drawTexturedModalRect(i1 + i2 + this.linePosX * this.colorSize + l4 *
	 * this.colorSize, j1 + j2 + this.linePosY * this.colorSize + l3 *
	 * this.colorSize, this.colorSize * 16, this.ySize + 10, this.colorSize,
	 * this.colorSize); } } } else { for (int l3 = 0; l3 > l1 - this.linePosY -
	 * 1; l3--) { for (int l4 = 0; l4 > l2 - this.linePosX - 1; l4--) {
	 * drawTexturedModalRect(i1 + i2 + this.linePosX * this.colorSize + l4 *
	 * this.colorSize, j1 + j2 + this.linePosY * this.colorSize + l3 *
	 * this.colorSize, this.color * this.colorSize, this.ySize + 10,
	 * this.colorSize, this.colorSize); drawTexturedModalRect(i1 + i2 +
	 * this.linePosX * this.colorSize + l4 * this.colorSize, j1 + j2 +
	 * this.linePosY * this.colorSize + l3 * this.colorSize, this.colorSize *
	 * 16, this.ySize + 10, this.colorSize, this.colorSize); } } } } } }
	 * drawTexturedModalRect(i1 + this.xSize + 5, j1, this.xSize, 0,
	 * this.xSize2, this.ySize2); for (int k = 0; k < 17; k++) {
	 * drawTexturedModalRect(i1 + this.xSize + 13, j1 + 35 + k * 11, k * 10,
	 * this.ySize, 10, 10); } if (this.color == 16) { drawTexturedModalRect(i1 +
	 * this.xSize + 13, j1 + 8, 0, this.ySize, 10, 10); } else {
	 * drawTexturedModalRect(i1 + this.xSize + 13, j1 + 8, 10 + 10 * this.color,
	 * this.ySize, 10, 10); } drawTexturedModalRect(i1 - 55, j1 + 89, this.xSize
	 * - 56, this.ySize, 56, 22); drawTexturedModalRect(i1 - 55, j1 + 111,
	 * this.xSize - 56, this.ySize + 4, 56, 15); drawTexturedModalRect(i1 - 55,
	 * j1 + 126, this.xSize - 56, this.ySize + 4, 56, 22);
	 *
	 * drawTexturedModalRect(i1 - 55, j1 + 150, this.xSize - 56, this.ySize, 56,
	 * 22); drawTexturedModalRect(i1 - 55, j1 + 172, this.xSize - 56, this.ySize
	 * + 4, 56, 15); drawTexturedModalRect(i1 - 55, j1 + 187, this.xSize - 56,
	 * this.ySize + 4, 56, 18); drawTexturedModalRect(i1 - 55, j1 + 205,
	 * this.xSize - 56, this.ySize + 4, 56, 22); drawTexturedModalRect(i1 - 40,
	 * j1 + 164, this.xSize, this.ySize, 26, 18);
	 *
	 * this.fontRendererObj.drawString("" + this.brushSize, i1 + this.xSize +
	 * 15, j1 + 20, 1052688); this.fontRendererObj.drawString("Brush", i1 - 41,
	 * j1 + 96, 1052688); this.fontRendererObj.drawString("Size", i1 - 38, j1 +
	 * 106, 1052688); this.fontRendererObj.drawString("" + this.currentLayer, i1
	 * - 33, j1 + 168, 1052688); this.fontRendererObj.drawString("Layer", i1 -
	 * 41, j1 + 155, 1052688); super.drawScreen(i, j, f); }
	 *
	 * protected void keyTyped(char c, int i) throws IOException {
	 * super.keyTyped(c, i); if (i == 18) { this.drawingOptions = 2; initGui();
	 * } if (i == 19) { this.drawingOptions = 1; initGui(); } if (i == 32) {
	 * this.drawingOptions = 0; initGui(); } if (i == 46) { copy(); } if (i ==
	 * 47) { paste(); } }
	 *
	 * public boolean doesGuiPauseGame() { return false; }
	 *
	 * private void copy() { for (int l1 = 0; l1 < this.yLength; l1++) { for
	 * (int l2 = 0; l2 < this.xLength; l2++) { this.copy[l1][l2] =
	 * this.blocks[this.currentLayer][l1][l2]; } } }
	 *
	 * private void paste() { save(); for (int l1 = 0; l1 < this.yLength; l1++)
	 * { for (int l2 = 0; l2 < this.xLength; l2++) {
	 * this.blocks[this.currentLayer][l1][l2] = this.copy[l1][l2]; } } }
	 *
	 * private void save() { if (this.hasUndone) { this.field_353_b =
	 * this.lastSave; } if (this.lastSave >= this.undoLength) { for (int l = 0;
	 * l < this.undoLength - 1; l++) { for (int layer = 0; layer < 100; layer++)
	 * { for (int l1 = 0; l1 < this.yLength; l1++) { for (int l2 = 0; l2 <
	 * this.xLength; l2++) { this.save[l][layer][l1][l2] = this.save[(l +
	 * 1)][layer][l1][l2]; } } } } this.lastSave -= 1; } else { this.field_353_b
	 * += 1; } for (int layer = 0; layer < 100; layer++) { for (int l1 = 0; l1 <
	 * this.yLength; l1++) { for (int l2 = 0; l2 < this.xLength; l2++) {
	 * this.save[this.lastSave][layer][l1][l2] = this.blocks[layer][l1][l2]; } }
	 * } this.lastSave += 1; }
	 *
	 * private void fillArea(int i, int j, int l) {
	 * this.blocks[this.currentLayer][j][i] = this.color; if ((i > 0) &&
	 * (this.blocks[this.currentLayer][j][(i - 1)] == l)) { fillArea(i - 1, j,
	 * l); } if ((i < this.xLength - 1) && (this.blocks[this.currentLayer][j][(i
	 * + 1)] == l)) { fillArea(i + 1, j, l); } if ((j > 0) &&
	 * (this.blocks[this.currentLayer][(j - 1)][i] == l)) { fillArea(i, j - 1,
	 * l); } if ((j < this.yLength - 1) && (this.blocks[this.currentLayer][(j +
	 * 1)][i] == l)) { fillArea(i, j + 1, l); } }
	 *
	 * public int xSize = 230; public int ySize = 230; public int xSize2 = 26;
	 * public int ySize2 = 230; public World worldObj; public int posX; public
	 * int posY; public int posZ; public int mData; public int direction; public
	 * int yLength = 44; public int xLength = 44; public int undoLength = 50;
	 * private int[][][] blocks = new int[100][this.yLength][this.xLength];
	 * private int[][] copy = new int[this.yLength][this.xLength]; private
	 * int[][][][] save = new
	 * int[this.undoLength][100][this.yLength][this.xLength]; private
	 * int[][][][] redo = new
	 * int[this.undoLength][100][this.yLength][this.xLength]; public int
	 * colorSize = 5; private int color; private int brushSize; private int
	 * currentLayer; private int drawingOptions; private int linePosX; private
	 * int linePosY; private int lastSave; private int field_353_b; private
	 * boolean hasUndone; private boolean[] visitedLayer = new boolean[100];
	 */
}
