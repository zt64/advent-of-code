LINES = File.read("./input/day-05.txt").lines

seeds = LINES[0].lchop("seeds: ").split(" ").map { |seed| seed.to_i64 }

seed_to_soil_index = LINES.index("seed-to-soil map:").not_nil!
soil_to_fertilizer_index = LINES.index("soil-to-fertilizer map:").not_nil!
fertilizer_to_water_index = LINES.index("fertilizer-to-water map:").not_nil!
water_to_light_index = LINES.index("water-to-light map:").not_nil!
light_to_temperature_index = LINES.index("light-to-temperature map:").not_nil!
temperature_to_humidity_index = LINES.index("temperature-to-humidity map:").not_nil!
humidity_to_location_index = LINES.index("humidity-to-location map:").not_nil!

struct Guh
  getter dst_start : Int64
  getter src_start : Int64
  getter length : Int64

  def initialize(@dst_start, @src_start, @length)
  end
end

def parse_map(start_index, end_index = nil)
  mapping = if end_index
              LINES[start_index + 1..end_index - 2]
            else
              LINES[start_index + 1...]
            end
  mapping = mapping.map do |line|
    dst_start, src_start, len = line.split(" ")
    Guh.new(dst_start.to_i64, src_start.to_i64, len.to_i64)
  end
  mapping
end

def map_value(mapping, value) : Int64
  mapping.each do |range|
    index = value - range.src_start
    if index >= 0 && index < range.length
      return range.dst_start + (value - range.src_start)
    end
  end

  return value
end

min = -1

maps = [] of Array(Guh)
SEED_TO_SOIL_MAP        = parse_map(seed_to_soil_index, soil_to_fertilizer_index)
SOIL_TO_FERTILIZER_MAP  = parse_map(soil_to_fertilizer_index, fertilizer_to_water_index)
FERTILIZER_TO_WATER_MAP = parse_map(fertilizer_to_water_index, water_to_light_index)
water_to_light_map = parse_map(water_to_light_index, light_to_temperature_index)
light_to_temperature_map = parse_map(light_to_temperature_index, temperature_to_humidity_index)
temperature_to_humidity_map = parse_map(temperature_to_humidity_index, humidity_to_location_index)
humidity_to_location_map = parse_map(humidity_to_location_index)

def process_seed(seed)
  soil = map_value(SEED_TO_SOIL_MAP, seed)
  fertilizer = map_value(SOIL_TO_FERTILIZER, soil)
  water = map_value(fertilizer_to_water_map, fertilizer)
  light = map_value(water_to_light_map, water)
  temperature = map_value(light_to_temperature_map, light)
  humidity = map_value(temperature_to_humidity_map, temperature)
  location = map_value(humidity_to_location_map, humidity)
end

seeds.each do |seed|
  location = process_seed(seed)
  if min == -1 || location < min
    min = location
  end
end

# 331445006
puts "Part 1: #{min}"

min = -1
split_seeds = seeds.in_slices_of(2)
split_seeds.each { |(start, len)|
  puts "Proccessing seed #{start}"

  spawn do
    (len - 1).times do |offset|
      seed = start + offset
      location = process_seed(seed, seed_to_soil_map, soil_to_fertilizer_map, fertilizer_to_water_map, water_to_light_map, light_to_temperature_map, temperature_to_humidity_map, humidity_to_location_map)
      if min == -1 || location < min
        min = location
      end
    end
  end
}

split_seeds.size.times do
  Fiber.yield
end

puts "Part 2: #{min}"
