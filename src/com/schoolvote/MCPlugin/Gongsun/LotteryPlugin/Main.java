package com.schoolvote.MCPlugin.Gongsun.LotteryPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Random;

public class Main extends JavaPlugin implements Listener {

    public Inventory inv;
    public int lott;
    Random r = new Random();

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Lottery Plugin is Disabled!" );
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Lottery Plugin by. TensorflowPepper is Enabled!" );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only Players can use these commands!");
            return true;
        } else {
            if(command.getName().equalsIgnoreCase("lottery")) {
                inv = Bukkit.createInventory(null, 9, "Lottery");
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "1", "§a복권 선택 1번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "2", "§a복권 선택 2번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "3", "§a복권 선택 3번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "4", "§a복권 선택 4번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "5", "§a복권 선택 5번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "6", "§a복권 선택 6번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "7", "§a복권 선택 7번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "8", "§a복권 선택 8번입니다!", "§b클릭하여 선택하세요!"));
                inv.addItem(createGuiItem(Material.GOLD_BLOCK, "9", "§a복권 선택 9번입니다!", "§b클릭하여 선택하세요!"));
                ((HumanEntity) sender).openInventory(inv);
                lott = r.nextInt(9);
                Bukkit.getLogger().info("Number is: " + (lott + 1));
            }
        }
        return true;
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws InterruptedException {
        if (e.getInventory() != inv) return;
        e.setCancelled(true);
        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        final Player p = (Player) e.getWhoClicked();
        if(p.getInventory().getItemInHand().getType() == Material.DIAMOND && p.getInventory().getItemInHand().getAmount() >= 8) {
            p.sendMessage(ChatColor.YELLOW + Integer.toString(e.getRawSlot() + 1) + "번 슬롯을 선택하셨습니다! 결과는...");
            Thread.sleep(2500);
            if (lott == e.getRawSlot()) {
                p.sendMessage(ChatColor.GREEN + "축하합니다! 성공했어요! (X4 다이아몬드)");
                p.getInventory().addItem(new ItemStack(Material.DIAMOND, p.getInventory().getItemInHand().getAmount() * 3));
            } else {
                p.sendMessage(ChatColor.RED + "아쉽습니다! 실패했네요...");
                p.getInventory().remove(p.getInventory().getItemInHand());
            }
        } else {
            p.sendMessage(ChatColor.AQUA + "복권을 사기 위해서는 손에 다이아몬드 8개 이상을 들고 있어야 해요...");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }
}
