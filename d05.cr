LINES = File.read("./input/d05.txt").lines

seeds = LINES[0].lchop("seeds: ").split(" ").map { |seed| seed.to_i64 }

SEED_TO_SOIL_INDEX            = LINES.index("seed-to-soil map:").not_nil!
SOIL_TO_FERTILIZER_INDEX      = LINES.index("soil-to-fertilizer map:").not_nil!
FERTILIZER_TO_WATER_INDEX     = LINES.index("fertilizer-to-water map:").not_nil!
WATER_TO_LIGHT_INDEX          = LINES.index("water-to-light map:").not_nil!
LIGHT_TO_TEMPERATURE_INDEX    = LINES.index("light-to-temperature map:").not_nil!
TEMPERATURE_TO_HUMIDITY_INDEX = LINES.index("temperature-to-humidity map:").not_nil!
HUMIDITY_TO_LOCATION_INDEX    = LINES.index("humidity-to-location map:").not_nil!

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
SEED_TO_SOIL_MAP            = parse_map(SEED_TO_SOIL_INDEX, SOIL_TO_FERTILIZER_INDEX)
SOIL_TO_FERTILIZER_MAP      = parse_map(SOIL_TO_FERTILIZER_INDEX, FERTILIZER_TO_WATER_INDEX)
FERTILIZER_TO_WATER_MAP     = parse_map(FERTILIZER_TO_WATER_INDEX, WATER_TO_LIGHT_INDEX)
WATER_TO_LIGHT_MAP          = parse_map(WATER_TO_LIGHT_INDEX, LIGHT_TO_TEMPERATURE_INDEX)
LIGHT_TO_TEMPERATURE_MAP    = parse_map(LIGHT_TO_TEMPERATURE_INDEX, TEMPERATURE_TO_HUMIDITY_INDEX)
TEMPERATURE_TO_HUMIDITY_MAP = parse_map(TEMPERATURE_TO_HUMIDITY_INDEX, HUMIDITY_TO_LOCATION_INDEX)
HUMIDITY_TO_LOCATION_MAP    = parse_map(HUMIDITY_TO_LOCATION_INDEX)

def process_seed(seed)
  soil = map_value(SEED_TO_SOIL_MAP, seed)
  fertilizer = map_value(SOIL_TO_FERTILIZER_MAP, soil)
  water = map_value(FERTILIZER_TO_WATER_MAP, fertilizer)
  light = map_value(WATER_TO_LIGHT_MAP, water)
  temperature = map_value(LIGHT_TO_TEMPERATURE_MAP, light)
  humidity = map_value(TEMPERATURE_TO_HUMIDITY_MAP, temperature)
  location = map_value(HUMIDITY_TO_LOCATION_MAP, humidity)
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
      location = process_seed(seed)
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
