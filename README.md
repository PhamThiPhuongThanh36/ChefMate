# ChefMate

Ứng dụng được viết bằng **Kotlin** với **Jetpack Compose** và **Room**,**Retrofit**.  
ChefMate cho phép **lưu trữ công thức nấu ăn, lập kế hoạch nguyên liệu và quản lý danh sách mua sắm** một cách tiện lợi.  

---

## Tính năng chính
- **Quản lý công thức nấu ăn**
  - Thêm công thức mới.  
  - Chỉnh sửa công thức có sẵn.  
  - Xóa công thức.  

- **Quản lý nguyên liệu mua sắm**
  - Thêm nguyên liệu thủ công (người dùng nhập trực tiếp).  
  - Thêm nguyên liệu tự động từ công thức nấu ăn đã chọn.  
  - Tự động gộp và xử lý các nguyên liệu **trùng lặp**.  

- **Quản lý danh sách mua sắm**
  - Tạo danh sách mua sắm.  
  - Thêm, xóa, chỉnh sửa nguyên liệu trong danh sách.  
  - Đánh dấu nguyên liệu đã mua / chưa mua.  

- **Lưu trữ dữ liệu với Room**
  - Dữ liệu được lưu local, không mất khi thoát app.  

- **UI hiện đại với Jetpack Compose**
  - Giao diện theo phong cách declarative, tự động cập nhật khi dữ liệu thay đổi.  

---

## 🛠 Công nghệ sử dụng
- [Kotlin] – ngôn ngữ chính.  
- [Jetpack Compose] – UI toolkit.  
- [Room] – ORM cho database.  
- [ViewModel] + [Flow] – quản lý state & dữ liệu.  
- [Navigation Compose] – điều hướng giữa các màn hình.  

---

## 📂 Cấu trúc dữ liệu (Room Entities)
```kotlin
@Entity(tableName = "Recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Int? = null,
    val userId: Int? = null,
    val recipeName: String,
    val image: String,
    val cookingTime: String,
    val ration: Int,
    val viewCount: Int,
    val likeQuantity: Int,
    val isPublic: Boolean,
    val createdAt: String,
)

@Entity(tableName = "Ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Int? = null,
    val recipeId: Int? = null,
    val ingredientName: String,
    val weight: String,
    val unit: String
)

@Entity(tableName = "Steps")
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val stepId: Int? = null,
    val recipeId: Int,
    val description: String
)

@Entity(tableName = "Tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val tagId: Int? = null,
    val recipeId: Int,
    val tagName: String
)

@Entity(tableName = "ShoppingLists")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true)
    val shoppingId: Int? = null,
    val status: Boolean,
    val createdAt: String,
)

@Entity(tableName = "ShoppingItems")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val siId: Int? = null,
    val shoppingId: Int? = null,
    val siName: String,
    val siWeight: String,
    val siUnit: String,
    val status: Boolean
)

